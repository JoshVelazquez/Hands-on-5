package examples.regresionLineal;

import jade.core.Agent;
import jade.core.behaviours.*;

public class regresionLinealAgent extends Agent{
    private int arrayX[], arrayY[];
    private double x, y, xy, xx, a, b;
    private int tamanio, prediccion;
    private regresionLinealGui myGui;
    private boolean terminado;

    protected void setup() {
        arrayX = new int[] { 651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518 };
        arrayY = new int[] { 23, 26, 30, 34, 43, 48, 52, 57, 58 };
        x = 0;
        y = 0;
        xx = 0;
        xy = 0;
        a = 0;
        b = 0;
        tamanio = arrayY.length;
        terminado = false;

        myGui = new regresionLinealGui(this);
        myGui.showGui();

        addBehaviour(new entrenar());
        addBehaviour(new predecir());
    }

    protected void takeDown() {
        myGui.dispose();
        System.out.println("Terminado!!!");
        terminado = true;
    }

    public void agregarPrediccion(final int pre) {
        prediccion = pre;
    }

    private class entrenar extends OneShotBehaviour {
        public void action() {
            for (int i = 0; i < tamanio; i++) {
                System.out.println("Dado " + arrayX[i] + " => " + arrayY[i]);
                x += arrayX[i];
                y += arrayY[i];
                xy += arrayX[i] * arrayY[i];
                xx += arrayX[i] * arrayX[i];
            }
    
            b = (tamanio * xy - x * y) / (tamanio * xx - x * x);
            a = (y - (b * x)) / tamanio;
        }
    }

    private class predecir extends Behaviour {
        public void action() {
            double resultado = a + (b * prediccion);
            if (prediccion != 0) {
                System.out.println(resultado + " = " + a + " + " + b + "(" + prediccion + ")");
                prediccion = 0;
            }
        }

        public boolean done() {
            if (terminado)
              return true;
            else
          return false;
          }

        public int onEnd() {
            myAgent.doDelete();   
            return super.onEnd();
          } 
    }
}
