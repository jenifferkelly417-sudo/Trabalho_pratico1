import java.time.LocalDateTime;

public class Veiculo {
    // Atributos privados para encapsulamento
    private String placa;
    private LocalDateTime horaEntrada;

    // Construtor: Método chamado ao criar um novo Veículo
    // Ele obriga a passar a placa e a hora logo de cara
    public Veiculo(String placa, LocalDateTime horaEntrada) {
        this.placa = placa;
        this.horaEntrada = horaEntrada;
    }

    // Getters: Métodos públicos para permitir que outras classes
    // leiam os valores, já que os atributos são privados
    public String getPlaca() {
        return placa;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }
}
