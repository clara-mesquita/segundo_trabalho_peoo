import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Disciplina {

    private String nome;
    private File diretorio;
    private File arquivo;
    private File alunos;

    public Disciplina(String nome) {
        this.nome = nome;
    }

    public void setNome (String nome){
        this.nome = nome;
    }

    public void criarDiretorio() {
        
        String nomeDiretorio = "c:\\" + this.nome;
        diretorio = new File(nomeDiretorio);
        this.diretorio.mkdir();
    }

    public void adicionarAluno(String resposta) throws IOException { 
        this.alunos = new File(this.diretorio, "alunos.txt");

        FileWriter fw = new FileWriter(this.alunos, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(resposta);
        bw.newLine();

        bw.close();
        fw.close();
    }

    public void gerarGabarito(String gabarito) throws IOException { 
        this.arquivo = new File(diretorio, "gabarito.txt"); 

        FileWriter fw = new FileWriter(this.arquivo, false);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(gabarito);
        bw.close();
        fw.close();
    }

    public String caminhoGabarito() {
        return this.arquivo.getAbsolutePath();
    }

    public int gerarNota(String resposta) throws IOException {

        int nota = 0, qtdV = 0, qtdF = 0;
        File g = new File(this.diretorio, "gabarito.txt");

        FileReader fr = new FileReader(g);
        BufferedReader br = new BufferedReader(fr);

        String gabarito = br.readLine();
        
        for(int i=0; i<10; i++) {
            if(resposta.charAt(i) == gabarito.charAt(i)) 
                nota++;

            if(resposta.charAt(i) == 'V') qtdV++;
            else qtdF++;
        }

        br.close();
        fr.close();

        if(qtdV == 10 || qtdF == 10)
            nota = 0;

        return nota;
    }

    public File montarNotas1() throws IOException { 

        File notas1 = new File(this.diretorio, "notas1.txt");
        File arq = new File(this.diretorio, "alunos.txt");

        FileReader fr = new FileReader(arq);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(notas1, false);
        BufferedWriter bw = new BufferedWriter(fw);

        ArrayList <Aluno> listaAuxiliar = new ArrayList<>(); 
        String linha = br.readLine();
        double media = 0; 
        while(linha != null) {
            String[] dados = linha.split("\t"); 
            
            int nota = gerarNota(dados[0]); 
            Aluno a = new Aluno(dados[1], nota);
            listaAuxiliar.add(a);
            media += nota;

            linha = br.readLine();
        }

        Collections.sort(listaAuxiliar, new SortByName()); 
        media /= listaAuxiliar.size();

        for(Aluno al : listaAuxiliar) {
            bw.write(al.getNome() + "\t" + al.getNota()); 
            bw.newLine();
        }

        bw.write("A media final da turma eh:" + "\t" + media);

        bw.close();
        fw.close();
        br.close();
        fr.close();

        return notas1;
    }

    public File montarNotas2() throws IOException { 

        File notas2 = new File(this.diretorio, "notas2.txt");
        File arq = new File(this.diretorio, "alunos.txt");

        FileReader fr = new FileReader(arq);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(notas2, false);
        BufferedWriter bw = new BufferedWriter(fw);

        ArrayList <Aluno> listaAuxiliar = new ArrayList<>();
        String linha = br.readLine(); 
        double media = 0;
        while(linha != null) {
            String[] dados = linha.split("\t"); 
            
            int nota = gerarNota(dados[0]); 
            Aluno a = new Aluno(dados[1], nota);
            listaAuxiliar.add(a);
            media += nota;

            linha = br.readLine();
        }

        Collections.sort(listaAuxiliar, new SortNotas()); 
        media /= listaAuxiliar.size();

        for(Aluno al : listaAuxiliar) {
            bw.write(al.getNome() + "\t" + al.getNota()); 
            bw.newLine();
        }

        bw.write("A media final da turma eh:" + "\t" + media);

        bw.close();
        fw.close();
        br.close();
        fr.close();
        
        return notas2;
    }

    public boolean arquivoExistente (String nomeDisciplina){
        String nomeArquivo = "c:\\" + nomeDisciplina; 
        return new File(nomeArquivo).exists();
    }
}