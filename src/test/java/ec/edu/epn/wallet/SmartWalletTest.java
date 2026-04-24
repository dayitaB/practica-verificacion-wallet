package ec.edu.epn.wallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartWalletTest {

    private SmartWallet wallet;

    @BeforeEach
    public void configurar() {
        wallet = new SmartWallet("Standard");
    }

    @Test
    public void testDepositoValidoSinCashback() {
        // depósito menor o igual a 100
        assertTrue(wallet.depositar(50.0));
        assertEquals(50.0, wallet.getSaldo(), 0.001);
        assertEquals("Activa", wallet.getEstado());
    }

    @Test
    public void testDepositoValidoConCashback() {
        // depósito mayor a 100
        assertTrue(wallet.depositar(200.0));
        // 200 > 100, entonces el cashback es 200 * 0.01 = 2.0. Total = 202.0
        assertEquals(202.0, wallet.getSaldo(), 0.001);
    }

    @Test
    public void testLimiteDepositoExactamente100() {
        // Límite  $100 no recibe cashback según las reglas (monto > 100)
        assertTrue(wallet.depositar(100.0));
        assertEquals(100.0, wallet.getSaldo(), 0.001);
    }

    @Test
    public void testLimiteDepositoExactamente5000() {
        // Límitr: $5,000 permitidos para usuario Standard.
        assertTrue(wallet.depositar(5000.0));
        // Recibe cashback por ser mayor a 100: 5000 * 0.01 = 50. Total = 5050
        assertEquals(5050.0, wallet.getSaldo(), 0.001);
    }

    @Test
    public void testErrorDepositoSuperaLimite() {
        // Error supera el límite de $5000 
        assertFalse(wallet.depositar(5001.0));
        assertEquals(0.0, wallet.getSaldo(), 0.001);
    }

    @Test
    public void testErrorDepositoNegativoOCero() {
        // Error: montos negativos y cero
        assertFalse(wallet.depositar(0.0));
        assertFalse(wallet.depositar(-10.0));
    }

    @Test
    public void testRetiroValido() {
        // retiro válido
        wallet.depositar(100.0);
        assertTrue(wallet.retirar(40.0));
        assertEquals(60.0, wallet.getSaldo(), 0.001);
    }

    @Test
    public void testErrorFondosInsuficientes() {
        // saldo insuficiente
        wallet.depositar(100.0);
        assertFalse(wallet.retirar(200.0));
        assertEquals(100.0, wallet.getSaldo(), 0.001);
    }

    @Test
    public void testErrorRetiroNegativoOCero() {
        // retiro con monto negativo o cero
        assertFalse(wallet.retirar(-5.0));
        assertFalse(wallet.retirar(0.0));
    }

    @Test
    public void testRetiroVaciaCuentaYLaInactiva() {
        // vaciar la cuenta la pone "Inactiva"
        wallet.depositar(50.0);
        assertTrue(wallet.retirar(50.0));
        assertEquals(0.0, wallet.getSaldo(), 0.001);
        assertEquals("Inactiva", wallet.getEstado());
    }
}