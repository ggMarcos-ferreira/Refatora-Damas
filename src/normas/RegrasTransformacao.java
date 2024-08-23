package normas;

import objetos.Casa;
import objetos.DamaCorBranca;
import objetos.DamaCorVermelha;
import objetos.Pedra;
import objetos.PedraCorBranca;
import objetos.PedraCorVermelha;

public class RegrasTransformacao {

    public boolean podeTransformarParaDama(Casa casa) {
        Pedra peca = casa.getPeca();

        if (peca instanceof PedraCorBranca) {
            return casa.getY() == 7;
        } else if (peca instanceof PedraCorVermelha) {
            return casa.getY() == 0;
        }

        return false;
    }

    public void transformarPedraParaDama(Casa casa) {
        Pedra peca = casa.getPeca();

        if (peca instanceof PedraCorBranca) {
            DamaCorBranca dama = new DamaCorBranca(casa);
            casa.colocarPeca(dama);
        } else if (peca instanceof PedraCorVermelha) {
            DamaCorVermelha dama = new DamaCorVermelha(casa);
            casa.colocarPeca(dama);
        }
    }
}
