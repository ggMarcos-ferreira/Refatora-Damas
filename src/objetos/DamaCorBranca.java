package objetos;

import gui.CasaGUI;

public class DamaCorBranca extends Pedra{
    public DamaCorBranca(Casa casa) {
        super(casa);
    }

    @Override
    public void desenhar(CasaGUI casaGUI) {
        casaGUI.desenharDamaBranca();
    }
    @Override
    public boolean isMovimentoValido(int vezAtual, Casa destino) {
        int distanciaX = Math.abs((destino.getX() - casa.getX()));
        int distanciaY = Math.abs((destino.getY() - casa.getY()));

        return vezAtual == 1 && distanciaX == distanciaY;
    }

}