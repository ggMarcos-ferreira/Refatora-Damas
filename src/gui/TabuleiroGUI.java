package gui;

import java.awt.Color;
import javax.swing.JPanel;

import objetos.*;
import controller.Jogo;

public class TabuleiroGUI extends JPanel {

    private JanelaPrincipal janela;
    private CasaGUI[][] casas;

    public TabuleiroGUI() {
    }

    public TabuleiroGUI(JanelaPrincipal janela) {
        this.janela = janela;
        initComponents();
        criarCasas();
    }

    private void criarCasas() {
        casas = new CasaGUI[8][8];
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Color cor = calcularCor(x, y);
                CasaGUI casa = new CasaGUI(x, y, cor, this);
                casas[x][y] = casa;
                add(casa);
            }
        }
    }

    private Color calcularCor(int x, int y) {
        if (x % 2 == 0) {
            if (y % 2 == 0) {
                return CasaGUI.COR_ESCURA;
            }
            else {
                return CasaGUI.COR_CLARA;
            }
        }
        else {
            if (y % 2 == 0) {
                return CasaGUI.COR_CLARA;
            }
            else {
                return CasaGUI.COR_ESCURA;
            }
        }
    }

    public void atualizar(Jogo jogo) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                CasaGUI casaGUI = casas[x][y];
                Tabuleiro tabuleiro = jogo.getTabuleiro();
                Casa casa = tabuleiro.getCasa(x, y);

                atualizarCasa(casa, casaGUI);
            }
        }
    }

    private void atualizarCasa(Casa casa, CasaGUI casaGUI) {
        if (casa.possuiPeca()) {
            Pedra peca = casa.getPeca();
            peca.desenhar(casaGUI);
        } else {
            casaGUI.apagarPeca();
        }
    }

    public JanelaPrincipal getJanela() {
        return janela;
    }

    private void initComponents() {
        setLayout(new java.awt.GridLayout(8, 8));
    }
}