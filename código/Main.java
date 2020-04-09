import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        
        
        
        int loop = -1; //entrar no loop no começo do programa
        
        System.out.println("Bem-vindo ao seu programa de notas escolares. Para ultiliza-lo nsira o numero de acordo com a opção que deseja.");
        
        while (loop != 0){
            Scanner lerDisciplina = new Scanner (System.in);
            Scanner ler =  new Scanner(System.in);
            Scanner lerResp = new Scanner (System.in);
            Disciplina d = new Disciplina("New");
            
            System.out.println("\nMenu\n"
                    + "\n1: Criar ou ver se ja existe a disciplina" //para poder inserir alunos, precisa-se criar o diretorio com o nome da disciplina
                    + "\n2: Inserir aluno;"
                    + "\n3: Gerar notas;"
                    + "\n0: Fechar programa.");
            loop = ler.nextInt();
            if (loop == 0)
                break;
            
            else if (loop == 1){
                System.out.println("\nInsira o nome da disciplina a ser consultada: ");
                String nomeDisciplina = lerDisciplina.nextLine();
                
                if (d.arquivoExistente(nomeDisciplina))
                    System.out.println("Essa disciplina ja existe e seu gabarito esta armazenado em: c:\\" + nomeDisciplina + "\\gabarito.txt"
                            + "\nPara reescrever seu gabarito, insira seu nome novamente na nova criação de uma nova disciplina");
                else 
                    System.out.println("Essa disciplina nao existe!");
                
                System.out.println("Se deseja voltar ao menu insira 0, se deseja criar uma nova disciplina insira 1.");
                    int escolhaDisciplina = lerResp.nextInt();
                    if (escolhaDisciplina == 1){
                        System.out.println("\nInsira o nome da nova disciplina: ");
                        nomeDisciplina = lerDisciplina.nextLine();
                        d.setNome(nomeDisciplina);
                        d.criarDiretorio();
                        System.out.print("Agora, insira o gabarito da disciplina, sem espaços e em caixa alta: ");
                        String gabarito = lerResp.next();
                        d.gerarGabarito(gabarito);
                        System.out.println("Seu gabarito for armazenado aqui: " + d.caminhoGabarito());
                    }
                    else if (escolhaDisciplina != 0)
                        System.out.println("Resposta invalida!");
                }
            
            else if (loop == 2){
                System.out.println("\nInsira o nome da disciplina na qual voce deseja adicionar alunos:");
                String nomeDisciplina =  lerDisciplina.nextLine();
                if (d.arquivoExistente(nomeDisciplina)){                
                    
                    int loopAluno = 1;
                    while (loopAluno != 0){
                        Scanner lerAux = new Scanner(System.in);
                        d.setNome(nomeDisciplina);
                        d.criarDiretorio();
                        System.out.println("\nInsira o nome do aluno:");
                        String nomeAluno = lerAux.nextLine();
                        System.out.print("Insira as respostas do aluno, sem espacos e em caixa alta:");
                        String respostasAluno = lerAux.next();
                        d.adicionarAluno(respostasAluno + "\t" + nomeAluno);
                        
                        System.out.println("Se deseja voltar ao menu inisira 0, se deseja adicionar outro aluno nesse disciplina, insira 1.");
                        loopAluno = ler.nextInt();
                    }
                }
                else
                    System.out.println ("Essa disciplina nao existe! Por favor, crie uma nova disciplina com esse nome.");
            }
             
            else if (loop == 3){
                System.out.println("Deseja montar notas de qual disciplina? ");
                String escolhaNotas = lerResp.nextLine();
                
                d.setNome(escolhaNotas);
                d.criarDiretorio();
                File notas1 = d.montarNotas1();
                File notas2 = d.montarNotas2();

                System.out.println("O arquivo com as notas em ordem alfabetica dos nomes dos alunos eh: " + notas1 + "\nO arquivo com as notas em ordem decrescente eh: " + notas2);

                System.out.println("Deseja visualizar os resultados na tela? Digite 'sim' ou 'nao' para escolher.");
                String escolha = ler.next();

                if(escolha.equals("sim")) {
                    
                    FileReader fr = new FileReader(notas1);
                    BufferedReader br = new BufferedReader(fr);

                    FileReader fr2 = new FileReader(notas2);
                    BufferedReader br2 = new BufferedReader(fr2);

                    try {
                        String linha = br.readLine();

                        while(linha != null) {
                            System.out.println(linha);
                            linha = br.readLine();
                        }

                        br.close();
                        fr.close();
                    }
                    catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }

                    System.out.println("------------------------------------");

                    try {
                        String linha2 = br2.readLine();

                        while(linha2 != null) {
                            System.out.println(linha2);
                            linha2 = br2.readLine();
                        }
                        
                        br2.close();
                        fr2.close();
                    }
                    catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
        
                }
                else if(escolha.equals("nao"))
                    continue;
                else
                    System.out.println("Escolha invalida!");

            }   
            else 
                System.out.println("Opcao invalida, tente novamente");
        
        }

    }
}