package objetos;

import gui.CasaGUI;

public abstract class Pedra {
    protected Casa casa;

    public Pedra(Casa casa) {
        this.casa = casa;
        casa.colocarPeca(this);
    }
    public abstract void desenhar(CasaGUI casaGUI);
    public abstract boolean isMovimentoValido(int vezAtual, Casa destino);

    public boolean isPecaIncompativel(Pedra outraPeca, Casa destino) {
        return outraPeca != null || destino.getPeca() != null;
    }
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }
    public int getSentido(){
        return 0;
    }
}
