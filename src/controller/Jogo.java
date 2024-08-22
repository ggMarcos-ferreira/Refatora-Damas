package controller;

import objetos.*;
import normas.deslocamento.RegrasDeslocamento;
import normas.transformacao.RegrasTransformacao;

public class Jogo {

    private RegrasTransformacao transformacao;
    private RegrasDeslocamento deslocamento; 
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private Tabuleiro tabuleiro;
    private int vezAtual = 1;
    private int jogadas = 0;
    private int jogadasSemComerPeca = 0;
    private Casa casaBloqueadaOrigem;

    public Jogo() {
        transformacao = new RegrasTransformacao();
        deslocamento = new RegrasDeslocamento(this);

        vezAtual = 1;
        jogadas = 0;
        jogadasSemComerPeca = 0;
        casaBloqueadaOrigem = null;

        jogadorUm = new Jogador("player branco");
        jogadorDois = new Jogador("player vermelho");

        tabuleiro = new Tabuleiro();
    }

    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Pedra peca = origem.getPeca();

        if (casaBloqueadaOrigem == null) {
            if (peca.isMovimentoValido(getVez(), destino) && deslocamento.simularMovimentoEValidar(origem, destino)) {
                realizarMovimento(peca, destino);
            }
        } else {
            processarCasaBloqueada(origem, destino);
        }
    }

    private void realizarMovimento(Pedra peca, Casa destino) {
        peca.mover(destino);

        if (!deslocamento.getPecasAComer().isEmpty()) {
            processarComerPecas(destino);
        } else {
            processarSemComerPeca();
        }

        jogadas++;
        processarTransformacao(destino);
    }

    private void processarComerPecas(Casa destino) {
        comerPecas();

        if (deslocamento.deveContinuarJogando(destino)) {
            casaBloqueadaOrigem = destino;
        } else {
            trocarDeVez();
        }
    }

    private void processarSemComerPeca() {
        jogadasSemComerPeca++;
        trocarDeVez();
    }

    private void processarCasaBloqueada(Casa origem, Casa destino) {
        if (origem.equals(casaBloqueadaOrigem) && deslocamento.simularMovimentoEValidar(origem, destino)) {
            if (!deslocamento.getPecasAComer().isEmpty()) {
                casaBloqueadaOrigem = null;
                moverPeca(origem.getX(), origem.getY(), destino.getX(), destino.getY());
            }
        }
    }

    private void processarTransformacao(Casa destino) {
        if (transformacao.podeTransformarParaDama(destino)) {
            transformacao.transformarPedraParaDama(destino);
        }
    }

    private void comerPecas() {
        int pecasComidas = deslocamento.getPecasAComer().size();

        if (getVez() == 1)
            jogadorUm.addPonto(pecasComidas);
        if (getVez() == 2)
            jogadorDois.addPonto(pecasComidas);

        for (Casa casa : deslocamento.getPecasAComer()) {
            casa.removerPeca();
        }

        deslocamento.getPecasAComer().removeAll(deslocamento.getPecasAComer());

        jogadasSemComerPeca = 0;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setJogadorUm(Jogador jogador) {
        jogadorUm = jogador;
    }

    public void setJogadorDois(Jogador jogador) {
        jogadorDois = jogador;
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    public int getJogadasSemComerPecas() {
        return jogadasSemComerPeca;
    }

    public int getJogada() {
        return jogadas;
    }

    private Casa getCasaBloqueada() {
        return casaBloqueadaOrigem;
    }

    public int getVez() {
        return vezAtual;
    }

    public void trocarDeVez() {
        if (vezAtual == 1) {
            vezAtual = 2;
        } else {
            vezAtual = 1;
        }
    }

    public int getGanhador() {
        if (jogadorUm.getPontos() == 12)
            return 1;
        if (jogadorDois.getPontos() == 12)
            return 2;
        return 0;
    }

    @Override
    public String toString() {
        String retorno = "Vez: ";
        if (getVez() == 1) {
            retorno += jogadorUm.getNome();
            retorno += "\n";
        } else if (getVez() == 2) {
            retorno += jogadorDois.getNome();
            retorno += "\n";
        }

        retorno += "Nº de jogadas: " + getJogada() + "\n";
        retorno += "Jogadas sem comer peça: " + getJogadasSemComerPecas() + "\n";
        retorno += "\n";
        retorno += "Informações do(a) jogador(a) " + jogadorUm.getNome() + "\n";
        retorno += "Pontos: " + jogadorUm.getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - jogadorDois.getPontos()) + "\n";
        retorno += "\n";
        retorno += "Informações do(a) jogador(a) " + jogadorDois.getNome() + "\n";
        retorno += "Pontos: " + jogadorDois.getPontos() + "\n";
        retorno += "Nº de peças restantes: " + (12 - jogadorUm.getPontos()) + "\n";

        if (casaBloqueadaOrigem != null) {
            retorno += "\n";
            retorno += "Mova a peça na casa " + casaBloqueadaOrigem.getX() + ":" + casaBloqueadaOrigem.getY() + "!";
        }

        return retorno;
    }
}
