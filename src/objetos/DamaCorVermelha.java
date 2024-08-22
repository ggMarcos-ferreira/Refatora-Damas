package objetos;

import gui.CasaGUI;

public class DamaCorVermelha extends Pedra{
    public DamaCorVermelha(Casa casa) {
        super(casa);
    }

    @Override
    public void desenhar(CasaGUI casaGUI) {
        casaGUI.desenharDamaVermelha();
    }
    @Override
    public boolean isMovimentoValido(int vezAtual, Casa destino) {
        int distanciaX = Math.abs((destino.getX() - casa.getX()));
        int distanciaY = Math.abs((destino.getY() - casa.getY()));

        return vezAtual == 2 && distanciaX == distanciaY;
    }

}