package mines;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board extends JPanel {
	private static final long serialVersionUID = 6195235521361212179L;
	
	static private final int numImages1 = 13;
    static private final int cellSize = 15;

  static private final int coverForCell = 10;
    static private final int markForCell = 10;
   static private final int emptyCell = 0;
   static private final int mineCell = 9;
   static private final int coveredMineCell = mineCell + coverForCell;
   static private final int markedMineCell =  coveredMineCell + markForCell;

   static private final int drawMine = 9;
   static private final int drawCover = 10;
   static private final int drawMark = 11;
   static private final int drawWrongMark= 12;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img11;
    private int mines = 40;
    private int rows = 16;
    private int cols = 16;
    private int allCells;
    private JLabel statusbar;


    private JLabel statusbar1; // Barre d'état pour afficher les informations
private static final int  numImages= 10; // Nombre total d'images, ajuster si nécessaire
private Image[] img1; // Tableau d'images

public Board(JLabel statusbar) {
    this.statusbar = statusbar; // Initialisation de la barre d'état
    initializeImages(); // Initialisation des images
    initializeBoard(); // Initialisation du plateau de jeu
}

// Initialise les images à partir des fichiers GIF
private void initializeImages() {
    img11 = new Image[numImages1]; // Crée le tableau d'images avec la taille spécifiée
    for (int i = 0; i < numImages1; i++) {
        String imagePath = String.format("%d.gif", i); // Chemin de l'image basé sur l'index
        img11[i] = loadImage(imagePath); // Charge l'image à partir du fichier et l'ajoute au tableau
    }
}

// Charge une image à partir d'un fichier
private Image loadImage(String imagePath) {
    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath)); // Crée une icône à partir du fichier
    return icon.getImage(); // Renvoie l'image extraite de l'icône
}

// Initialise le plateau de jeu en configurant le double buffering, le gestionnaire de souris et en lançant une nouvelle partie
private void initializeBoard() {
    setDoubleBuffered(true); // Active le double buffering pour un rendu plus fluide
    addMouseListener(new MinesAdapter()); // Ajoute un gestionnaire de souris pour les interactions du jeu
    newGame(); // Démarre une nouvelle partie
}



    public void newGame() {

        Random random;
        int currentCol;

      int i = 0;
int position = 0;
int cell = 0;
random = new Random();
inGame = true;
minesLeft = mines;
allCells = rows * cols;
field = new int[allCells];

for (i = 0; i < allCells; i++)
    field[i] = coverForCell;
statusbar.setText(Integer.toString(minesLeft));
i = 0;
while (i < mines) {
    position = random.nextInt(allCells);

            if ((position < allCells) &&
                (field[position] != coveredMineCell)) {


                currentCol = position % cols;
                field[position] = coveredMineCell;
                i++;

                if (currentCol > 0) { 
                    cell = position - 1 - cols;
                    if (cell >= 0)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                    cell = position - 1;
                    if (cell >= 0)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;

                    cell = position + cols - 1;
                    if (cell < allCells)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                }

                cell = position - cols;
                if (cell >= 0)
                    if (field[cell] != coveredMineCell)
                        field[cell] += 1;
                cell = position + cols;
                if (cell < allCells)
                    if (field[cell] != coveredMineCell)
                        field[cell] += 1;

                if (currentCol < (cols - 1)) {
                    cell = position - cols + 1;
                    if (cell >= 0)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                    cell = position + cols + 1;
                    if (cell < allCells)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                    cell = position + 1;
                    if (cell < allCells)
                        if (field[cell] != coveredMineCell)
                            field[cell] += 1;
                }
            }
        }
    }


   public void findEmptyCells(int j) {
    int currentCol = j % cols; // Colonne actuelle de la cellule j

    // Vérifie les cellules adjacentes et les traite
    checkAdjacentCell(j, j - cols - 1); // Cellule en haut à gauche
    checkAdjacentCell(j, j - 1); // Cellule à gauche
    checkAdjacentCell(j, j + cols - 1); // Cellule en bas à gauche
    checkAdjacentCell(j, j - cols); // Cellule au-dessus
    checkAdjacentCell(j, j + cols); // Cellule en dessous
    checkAdjacentCell(j, j - cols + 1); // Cellule en haut à droite
    checkAdjacentCell(j, j + cols + 1); // Cellule en bas à droite
    checkAdjacentCell(j, j + 1); // Cellule à droite
}

// Vérifie une cellule adjacente et la traite si elle est valide et non révélée
private void checkAdjacentCell(int currentCell, int adjacentCell) {
    if (isCellValid(adjacentCell) && field[adjacentCell] > mineCell) {
        field[adjacentCell] -= coverForCell; // Révèle la cellule adjacente
        if (field[adjacentCell] == emptyCell) {
            findEmptyCells(adjacentCell); // Si la cellule adjacente est vide, récursion pour trouver les autres cellules vides
        }
    }
}

// Vérifie si une cellule est valide (dans les limites du tableau)
private boolean isCellValid(int cell) {
    return cell >= 0 && cell < allCells;
}

public void paint11(Graphics g) {
    int cell = 0;
    int uncover = 0;

    // Parcours des cellules
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int index = (i * cols) + j;
            cell = field[index];

            // Gestion de l'état de la cellule
            handleCellState1(cell);

            if (inGame) {
                // En jeu
                if (cell > coveredMineCell) {
                    cell = drawMark;
                } else if (cell > mineCell) {
                    cell = drawCover;
                    uncover++;
                }
            } else {
                // Fin de partie
                handleEndGameState1(cell);
            }

            g.drawImage(img11[cell], (j * cellSize), (i * cellSize), this);
        }
    }

    // Mise à jour de la barre de statut
    updateStatusbar1(uncover);
}

private void handleCellState1(int cell) {
    // Gestion de l'état de la cellule lorsque le jeu est en cours
    if (!inGame) {
        if (cell == coveredMineCell) {
            cell = drawMine;
        } else if (cell == markedMineCell) {
            cell = drawMark;
        } else if (cell > coveredMineCell) {
            cell = drawWrongMark;
        } else if (cell > mineCell) {
            cell = drawCover;
        }
    } else {
        // Gestion de l'état de la cellule lorsqu'il s'agit d'une mine
        if (cell == mineCell) {
            inGame = false;
        }
    }
}

private void handleEndGameState1(int cell) {
    // Gestion de l'état de la cellule à la fin de la partie
    if (cell == coveredMineCell) {
        cell = drawMine;
    } else if (cell == markedMineCell) {
        cell = drawMark;
    } else if (cell > coveredMineCell) {
        cell = drawWrongMark;
    } else if (cell > mineCell) {
        cell = drawCover;
    }
}

private void updateStatusbar1(int uncover) {
    // Mise à jour de la barre de statut en fonction du nombre de cellules non découvertes
    if (uncover == 0 && inGame) {
        inGame = false;
        statusbar.setText("Game won");
    } else if (!inGame) {
        statusbar.setText("Game lost");
    }
}
@Override
public void paint(Graphics g) {
    int cell = 0;
    int uncover = 0;

    // Parcours des cellules
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int index = (i * cols) + j;
            cell = field[index];

            // Gestion de l'état de la cellule
            handleCellState1(cell);

            if (inGame) {
                // En jeu
                if (cell > coveredMineCell) {
                    cell = drawMark;
                } else if (cell > mineCell) {
                    cell = drawCover;
                    uncover++;
                }
            } else {
                // Fin de partie
                handleEndGameState1(cell);
            }

            g.drawImage(img11[cell], (j * cellSize), (i * cellSize), this);
        }
    }

    // Mise à jour de la barre de statut
    updateStatusbar1(uncover);
}

private void handleCellState(int cell) {
    // Gestion de l'état de la cellule lorsque le jeu est en cours
    if (!inGame) {
        if (cell == coveredMineCell) {
            cell = drawMine;
        } else if (cell == markedMineCell) {
            cell = drawMark;
        } else if (cell > coveredMineCell) {
            cell = drawWrongMark;
        } else if (cell > mineCell) {
            cell = drawCover;
        }
    } else {
        // Gestion de l'état de la cellule lorsqu'il s'agit d'une mine
        if (cell == mineCell) {
            inGame = false;
        }
    }
}

private void handleEndGameState(int cell) {
    // Gestion de l'état de la cellule à la fin de la partie
    if (cell == coveredMineCell) {
        cell = drawMine;
    } else if (cell == markedMineCell) {
        cell = drawMark;
    } else if (cell > coveredMineCell) {
        cell = drawWrongMark;
    } else if (cell > mineCell) {
        cell = drawCover;
    }
}

private void updateStatusbar(int uncover) {
    // Mise à jour de la barre de statut en fonction du nombre de cellules non découvertes
    if (uncover == 0 && inGame) {
        inGame = false;
        statusbar.setText("Game won");
    } else if (!inGame) {
        statusbar.setText("Game lost");
    }
}



  class MinesAdapter extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int cCol = x / cellSize;
        int cRow = y / cellSize;

        boolean rep = false;

        // Nouvelle partie si le jeu est terminé
        if (!inGame) {
            newGame();
            repaint();
        }

        // Vérifier si les coordonnées sont valides
        if ((x < cols * cellSize) && (y < rows * cellSize)) {

            // Clic droit de la souris
            if (e.getButton() == MouseEvent.BUTTON3) {
                // Marquer/démarquer une cellule
                if (field[(cRow * cols) + cCol] > mineCell) {
                    rep = true;

                    if (field[(cRow * cols) + cCol] <= coveredMineCell) {
                        // Marquer une cellule
                        if (minesLeft > 0) {
                            field[(cRow * cols) + cCol] += markForCell;
                            minesLeft--;
                            statusbar.setText(Integer.toString(minesLeft));
                        } else {
                            statusbar.setText("No marks left");
                        }
                    } else {
                        // Démarquer une cellule
                        field[(cRow * cols) + cCol] -= markForCell;
                        minesLeft++;
                        statusbar.setText(Integer.toString(minesLeft));
                    }
                }

            } else {
                // Clic gauche de la souris
                if (field[(cRow * cols) + cCol] > coveredMineCell) {
                    return;
                }

                if ((field[(cRow * cols) + cCol] > mineCell) &&
                    (field[(cRow * cols) + cCol] < markedMineCell)) {
                    // Révéler une cellule
                    field[(cRow * cols) + cCol] -= coverForCell;
                    rep = true;

                    if (field[(cRow * cols) + cCol] == mineCell) {
                        inGame = false;
                    }
                    if (field[(cRow * cols) + cCol] == emptyCell) {
                        findEmptyCells((cRow * cols) + cCol);
                    }
                }
            }

            if (rep) {
                repaint();
            }
        }
    }
}
}
