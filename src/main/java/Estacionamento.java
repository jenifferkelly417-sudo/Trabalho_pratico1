import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    // Lista dinâmica para guardar os veículos (substitui o vetor/array fixo)
    private List<Veiculo> veiculos;
    private int capacidadeTotal;
    private double totalArrecadado;

    // Valores Fixos para facilitar a alteração de preços no futuro
    private static final double PRECO_PRIMEIRA_HORA = 12.00;
    private static final double PRECO_HORA_ADICIONAL = 8.00;

    // Construtor: Inicia a lista vazia e define o tamanho do estacionamento
    public Estacionamento(int capacidadeTotal) {
        this.veiculos = new ArrayList<>();
        this.capacidadeTotal = capacidadeTotal;
        this.totalArrecadado = 0.0;
    }

    // Calcula quantas vagas sobram subtraindo o tamanho da lista da capacidade
    public int getVagasDisponiveis() {
        return capacidadeTotal - veiculos.size();
    }

    public void registrarEntrada(Veiculo veiculo) {
        // Verifica se tem vaga antes de deixar entrar
        if (getVagasDisponiveis() > 0) {
            veiculos.add(veiculo); // Adiciona na lista
            System.out.println("Veículo " + veiculo.getPlaca() + " entrou. Vagas restantes: " + getVagasDisponiveis());
        } else {
            System.out.println("Erro: Estacionamento cheio!");
        }
    }

    // Método auxiliar para procurar um carro na lista pela placa
    public Veiculo buscarVeiculo(String placa) {
        // Percorre toda a lista de veículos
        for (Veiculo v : veiculos) {
            // Compara as Strings ignorando maiúsculas e minúsculas
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v; // Retorna o objeto veículo se achar
            }
        }
        return null; // Retorna nulo se não achar nada
    }

    public void registrarSaida(String placa) {
        // Primeiro busca se o veículo existe
        Veiculo veiculo = buscarVeiculo(placa);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado com a placa " + placa);
            return; // Sai do método se não achar o carro
        }

        LocalDateTime horaSaida = LocalDateTime.now();

        // --- Trecho de código ajustado a partir de sugestão do gemini ---
        // Usa ChronoUnit para pegar a diferença exata em minutos entre entrada e saída
        long minutosTotais = ChronoUnit.MINUTES.between(veiculo.getHoraEntrada(), horaSaida);

        // Regra: se o tempo for 0 ou negativo (muito rápido), cobra 1 min para não dar erro
        if (minutosTotais <= 0) minutosTotais = 1;

        // Math.ceil arredonda para cima (ex: 61 min vira 2 horas)
        long horasCobradas = (long) Math.ceil(minutosTotais / 60.0);
        // --- Fim da sugestão ---

        // Lógica de cobrança: 1ª hora é preço fixo, o resto é adicional
        double valorPagar;
        if (horasCobradas == 1) {
            valorPagar = PRECO_PRIMEIRA_HORA;
        } else {
            // Fórmula: 12 + (horas extras * 8)
            valorPagar = PRECO_PRIMEIRA_HORA + (horasCobradas - 1) * PRECO_HORA_ADICIONAL;
        }

        // Soma no caixa e remove o carro da lista
        totalArrecadado += valorPagar;
        veiculos.remove(veiculo);

        // Imprime o recibo
        System.out.println("--------------------------------");
        System.out.println("SAÍDA REGISTRADA: " + veiculo.getPlaca());
        System.out.println("Tempo: " + minutosTotais + " min (" + horasCobradas + " horas a pagar)");
        System.out.printf("Valor: R$ %.2f%n", valorPagar);
        System.out.println("--------------------------------");
    }

    // Retorna a lista inteira para podermos imprimir no Main
    public List<Veiculo> getVeiculosEstacionados() {
        return veiculos;
    }

    public double getTotalArrecadado() {
        return totalArrecadado;
    }
}
