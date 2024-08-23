package normas;

import objetos.*;

import java.util.ArrayList;

public class RegrasDeslocamento {
    private final Jogo jogo;
    private final ArrayList<Casa> pecasAComer;

    public RegrasDeslocamento(Jogo jogo){
        this.jogo = jogo;
        pecasAComer = new ArrayList<>();

    }

    public ArrayList<Casa> getPecasAComer() {
        return pecasAComer;
    }

    public boolean simularMovimentoEValidar(Casa origem, Casa destino) {
        Pedra peca = origem.getPeca();
        int casasComPecaSeguidas = 0;

        int vezAtual = jogo.getVez();

        if (destino.getPeca() != null) return false;

        int sentidoX = (destino.getX() - origem.getX());
        int sentidoY = (destino.getY() - origem.getY());
        int distanciaX = Math.abs(sentidoX);
        int distanciaY = Math.abs(sentidoY);

        if ((distanciaX == 0) || (distanciaY == 0)) return false;

        sentidoX = sentidoX/distanciaX;
        sentidoY = sentidoY/distanciaY;

        if (distanciaX == 2 && distanciaY == 2 && peca.isMovimentoValido(vezAtual, destino)) {

            Casa casa = jogo.getTabuleiro().getCasa((destino.getX() - sentidoX), (destino.getY() - sentidoY));

            if (casa.getPeca() == null){
                return false;
            }
        } else {
            if (peca.getSentido() == 1) {
                return (distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY);
            } else {
                if (peca.getSentido() == -1) {
                    return (distanciaX == 1 || distanciaY == 1) && (distanciaX == distanciaY);
                }
            }
        }

        int i = origem.getX();
        int j = origem.getY();

        while (!((i == destino.getX()) || (j == destino.getY()))) {
            i += sentidoX;
            j += sentidoY;

            Casa alvo = jogo.getTabuleiro().getCasa(i, j);
            Pedra pecaAlvo = alvo.getPeca();

            if (!(pecaAlvo == null)) {
                casasComPecaSeguidas += 1;

                if(peca.isMovimentoValido(vezAtual, destino) && pecaAlvo.isMovimentoValido(vezAtual, destino)){
                    if (!pecasAComer.isEmpty()) pecasAComer.removeAll(pecasAComer);
                    return false;
                }
            } else {
                if (casasComPecaSeguidas == 1) {
                    Casa casa = jogo.getTabuleiro().getCasa((alvo.getX() - sentidoX), (alvo.getY() - sentidoY));
                    pecasAComer.add(casa);
                }
                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                if (!pecasAComer.isEmpty()) pecasAComer.removeAll(pecasAComer);
                return false;
            }
        }
        return true;
    }

    private boolean isMovimentoValido(Casa origem, int deltaX, int deltaY) {
        Pedra peca = origem.getPeca();
        int x = origem.getX();
        int y = origem.getY();
        int pecasSeguidasNoCaminho = 0;

        try {
            while (isDentroDosLimites(x, y)) {
                x += deltaX;
                y += deltaY;

                if (isCasaVazia(x, y)) {
                    return pecasSeguidasNoCaminho == 1;
                }

                Pedra pecaAtual = jogo.getTabuleiro().getCasa(x, y).getPeca();
                if (isPecaIncompativel(peca, pecaAtual, x, y, deltaX, deltaY)) {
                    return false;
                }

                pecasSeguidasNoCaminho++;
                if (pecasSeguidasNoCaminho > 1) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
    private boolean isDentroDosLimites(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
    private boolean isCasaVazia(int x, int y) {
        return jogo.getTabuleiro().getCasa(x, y).getPeca() == null;
    }

    private boolean isPecaIncompativel(Pedra pecaOrigem, Pedra pecaAtual, int x, int y, int deltaX, int deltaY) {
        return pecaOrigem.isPecaIncompativel(pecaAtual, jogo.getTabuleiro().getCasa(x + deltaX, y + deltaY));
    }

    public boolean deveContinuarJogando(Casa origem) {
        int[][] direcoes = {{Tabuleiro.X_ESQUERDA, Tabuleiro.Y_CIMA},
                {Tabuleiro.X_DIREITA, Tabuleiro.Y_CIMA},
                {Tabuleiro.X_DIREITA, Tabuleiro.Y_BAIXO},
                {Tabuleiro.X_ESQUERDA, Tabuleiro.Y_BAIXO}};

        for (int[] direcao : direcoes) {
            if (isMovimentoValido(origem, direcao[0], direcao[1])) {
                return true;
            }
        }

        return false;
    }

}