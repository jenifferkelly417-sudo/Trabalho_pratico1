import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    // --- Trecho de código ajustado a partir de sugestão do gemini ---
    // Cria um formatador para imprimir datas no padrão brasileiro (dia/mês hora:minuto)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM HH:mm");

    public static void main(String[] args) {
        // Cria o estacionamento com capacidade de 10 vagas
        Estacionamento estacionamento = new Estacionamento(10);
        Scanner scanner = new Scanner(System.in);
        int opcao;

        // Loop "do-while" garante que o menu apareça pelo menos uma vez
        do {
            System.out.println("\n=== ESTACIONAMENTO DUNA ===");
            System.out.println("1 - Entrada de Veículo");
            System.out.println("2 - Saída de Veículo");
            System.out.println("3 - Ver Vagas");
            System.out.println("4 - Listar Veículos");
            System.out.println("5 - Buscar Placa");
            System.out.println("6 - Relatório Financeiro");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            // LÊ DIRETAMENTE O INTEIRO.
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Placa do veículo: ");
                    String placa = scanner.nextLine();

                    System.out.println("1 - Hora atual | 2 - Hora manual (teste)");
                    System.out.print("Escolha: ");

                    int tipoHora = scanner.nextInt(); // Assume que é um número (1 ou 2)
                    scanner.nextLine();

                    LocalDateTime dataEntrada;
                    if (tipoHora == 2) {
                        System.out.print("Hora (0-23): ");
                        int h = scanner.nextInt();
                        System.out.print("Minuto (0-59): ");
                        int m = scanner.nextInt();
                        scanner.nextLine();

                        // --- Trecho de código ajustado a partir de sugestão do gemini ---
                        // Monta uma data personalizada para podermos testar o cálculo de preço
                        dataEntrada = LocalDateTime.now().withHour(h).withMinute(m).withSecond(0);
                    } else {
                        // Pega a hora exata do computador
                        dataEntrada = LocalDateTime.now();
                    }

                    Veiculo novo = new Veiculo(placa.toUpperCase(), dataEntrada);
                    estacionamento.registrarEntrada(novo);
                    break;

                case 2:
                    System.out.print("Placa para saída: ");
                    String placaSaida = scanner.nextLine();
                    estacionamento.registrarSaida(placaSaida.toUpperCase());
                    break;

                case 3:
                    System.out.println("Livres: " + estacionamento.getVagasDisponiveis());
                    System.out.println("Ocupadas: " + estacionamento.getVeiculosEstacionados().size());
                    break;

                case 4:
                    if (estacionamento.getVeiculosEstacionados().isEmpty()) {
                        System.out.println("Estacionamento vazio.");
                    } else {
                        System.out.println("--- Lista de Veículos ---");
                        // Loop para mostrar todos os itens da lista
                        for (Veiculo v : estacionamento.getVeiculosEstacionados()) {
                            // Usa o FORMATTER criado lá em cima para a data ficar bonita
                            System.out.println(v.getPlaca() + " - Entrada: " + v.getHoraEntrada().format(FORMATTER));
                        }
                    }
                    break;

                case 5:
                    System.out.print("Pesquisar placa: ");
                    String busca = scanner.nextLine();
                    Veiculo v = estacionamento.buscarVeiculo(busca.toUpperCase());
                    if (v != null) {
                        System.out.println("Achou! Está aqui desde: " + v.getHoraEntrada().format(FORMATTER));
                    } else {
                        System.out.println("Não está no estacionamento.");
                    }
                    break;

                case 6:
                    // %.2f formata o valor para 2 casas decimais
                    System.out.printf("Total Ganho: R$ %.2f%n", estacionamento.getTotalArrecadado());
                    break;

                case 0:
                    System.out.println("Fechando sistema...");
                    break;

                default:
                    // Caso o usuário digite um número que não é opção (ex: 9)
                    System.out.println("Opção inexistente!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}

