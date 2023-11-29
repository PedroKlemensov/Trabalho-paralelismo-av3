package Q1;

import java.util.Random;

public class Sensor {
    Random random = new Random();

    private String nome;
    private String tipo;

    private int dado= random.nextInt(101);;

    public Sensor(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDado() {
        return dado;
    }
}
