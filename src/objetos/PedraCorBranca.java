package objetos;

import gui.CasaGUI;

public class PedraCorBranca extends Pedra {
    public PedraCorBranca(Casa casa) {
        super(casa);
    }

    @Override
    public void desenhar(CasaGUI casaGUI) {
        casaGUI.desenharPedraBranca();
    }

    public int getSentido(){
        return 1;
    }

    @Override
    public boolean isMovimentoValido(int vezAtual, Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        if ((distanciaX == 0) || (distanciaY == 0)) return false;

        return vezAtual == 1 && (distanciaX <= 2 || distanciaY <= 2) && (distanciaX == distanciaY) && getSentido() == 1;
    }
}
