package com.hhplus.commerce.domain.account;

import com.hhplus.commerce.domain.account.entity.Account;
import com.hhplus.commerce.domain.account.model.AccountErrorCode;
import com.hhplus.commerce.domain.account.model.TransactionStatus;
import com.hhplus.commerce.domain.common.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountHistoryJpaRepository accountHistoryJpaRepository;

    private AccountService accountService;

    //    private AccountHistoryJpaRepository accountHistoryJpaRepository;
    @Mock
    private AccountHistoryService accountHistoryService;

    @BeforeEach
    void beforeEach() {
        accountService = new AccountServiceImpl(accountRepository, accountHistoryService);
    }


    @Test
    void 계좌ID로_정보를_조회한다() {
        //given
        Long accountId = 1L;
        Long balance = 1000L;
        when(accountRepository.findByIdOrElseThrow(1L)).thenReturn(Account.of(accountId, accountId, balance));
        // when
        Account account = accountService.getAccountInfo(accountId);
        // then
        assertThat(account.getId()).isEqualTo(accountId);
        assertThat(account.getBalance()).isEqualTo(balance);
    }


    @Test
    void 존재하지_않는_계좌ID로_조회_시도시_오류를_발생시켜야_한다() {
        //given
        //when
        Long accountId = 45678L;
        when(accountRepository.findByIdOrElseThrow(accountId)).thenThrow(new CustomException(AccountErrorCode.ACCOUNT_NOT_EXIST));

        // then
        assertThatThrownBy(() -> accountService.getAccountInfo(accountId))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void 계좌를_충전한다() {
        //given
        Long accountId = 1L;
        Long balance = 1000L;
        Long amount = 1000L;

        when(accountRepository.findByIdOrElseThrow(accountId)).thenReturn(Account.of(accountId, accountId, balance));
        when(accountRepository.save(any(Account.class))).thenReturn(Account.of(accountId, accountId, balance + amount));

        // when
        Account result = accountService.deposit(accountId, amount);

        // then
        assertThat(result.getId()).isEqualTo(accountId);
        assertThat(result.getBalance()).isEqualTo(balance + amount);
    }


    @Test
    void 계좌에서_출금한다() {
        //given
        Long accountId = 1L;
        Long balance = 1000L;
        Long amount = 500L;

        when(accountRepository.findByIdOrElseThrow(accountId)).thenReturn(Account.of(accountId, accountId, balance));
        when(accountRepository.save(any(Account.class))).thenReturn(Account.of(accountId, accountId, balance - amount));

        // when
        Account result = accountService.withdraw(accountId, amount);

        // then
        assertThat(result.getId()).isEqualTo(accountId);
        assertThat(result.getBalance()).isEqualTo(balance - amount);
        verify(accountHistoryService).saveWithdrawHistory(accountId, balance, amount, TransactionStatus.SUCCESS);

    }

    @Test
    void 존재하지_않는_계좌ID로_충전_시도시_오류를_발생시켜야_한다() {
        //given
        Long accountId = 45678L;
        Long amount = 1000L;
        when(accountRepository.findByIdOrElseThrow(accountId)).thenThrow(new CustomException(AccountErrorCode.ACCOUNT_NOT_EXIST));

        // when
        // then
        assertThatThrownBy(() -> accountService.deposit(accountId, amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(AccountErrorCode.ACCOUNT_NOT_EXIST.getMessage());
        verify(accountHistoryService, times(0)).saveDepositHistory(accountId, 0L, amount, TransactionStatus.SUCCESS);
    }

    @Test
    void 유효하지_않은_금액으로_충전_혹은_출금_시도시_오류를_발생시켜야_한다() {
        //given
        Long accountId = 1L;
        Long amount = -1000L;
        when(accountRepository.findByIdOrElseThrow(accountId)).thenReturn(Account.of(accountId, accountId, 1000L));

        // when
        // then
        // 충전 테스트
        assertThatThrownBy(() -> accountService.deposit(accountId, amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(AccountErrorCode.NOT_VALID_AMOUNT.getMessage());

        // 출금 테스트
        assertThatThrownBy(() -> accountService.withdraw(accountId, amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(AccountErrorCode.NOT_VALID_AMOUNT.getMessage());
        verify(accountHistoryService, times(0)).saveDepositHistory(accountId, 0L, amount, TransactionStatus.SUCCESS);

    }

    @Test
    void 소지금보다_큰_금액으로_사용_시도시_오류를_발생시켜야_한다() {
        //given
        Long accountId = 1L;
        Long balance = 0L;
        Long amount = 1000L;
        when(accountRepository.findByIdOrElseThrow(accountId)).thenReturn(Account.of(accountId, accountId, balance));

        // when
        // then
        assertThatThrownBy(() -> accountService.withdraw(accountId, amount))
                .isInstanceOf(CustomException.class)
                .hasMessage(AccountErrorCode.BALANCE_NOT_ENOUGH.getMessage());
        verify(accountHistoryService).saveWithdrawHistory(accountId, balance, amount, TransactionStatus.FAIL);
    }

}
