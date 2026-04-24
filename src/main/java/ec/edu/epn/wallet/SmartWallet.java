package ec.edu.epn.wallet;

public class SmartWallet {
    
    private double saldo;
    private String tipoUsuario;
    private String estado;

    public SmartWallet(String tipoUsuario) {
        this.saldo = 0.0;
        this.tipoUsuario = tipoUsuario;
        this.estado = "Activa";
    }

    public boolean depositar(double monto) {
        if (monto <= 0) {
            return false; // El monto debe ser mayor a 0
        }

        double saldoEsperado = this.saldo + monto;
        
        // Si el usuario es "Standard", el saldo máximo no puede superar los $5,000
        if ("Standard".equals(tipoUsuario) && saldoEsperado > 5000.0) {
            return false;
        }

        this.saldo += monto;

        // Si el monto es mayor a $100, el usuario recibe un "cashback" del 1%
        if (monto > 100) {
            this.saldo += (monto * 0.01);
        }

        return true;
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            return false; // No se pueden retirar montos negativos o cero
        }

        if (monto > this.saldo) {
            return false; // No se puede retirar más de lo que hay en el saldo
        }

        this.saldo -= monto;

        // Si el saldo queda en exactamente 0, la cuenta debe marcarse como "Inactiva"
        if (this.saldo == 0.0) {
            this.estado = "Inactiva";
        }

        return true;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getEstado() {
        return estado;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
