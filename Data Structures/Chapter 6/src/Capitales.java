/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 5. Hashing
  Pablo López
*/

//import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.tuple.Tuple2;

public class Capitales {

    public static void main(String[] args) {
        // compara la salida generada según se utilice un AVLDictionary o un HashDictionary
        //Dictionary<String, String> capitales = new AVLDictionary<>();
         Dictionary<String, String> capitales = new HashDictionary<>();
        capitales.insert("España", "Madrid");
        capitales.insert("Francia", "Paris");
        capitales.insert("Portugal", "Lisboa");
        capitales.insert("Italia", "Roma");
        capitales.insert("Alemania", "Berlin");

        System.out.println("usando un " + capitales.getClass().getSimpleName());
        for (Tuple2<String, String> c : capitales.keysValues()) {
            System.out.printf("%s -> %s\n", c._1(), c._2());
        }
    }
}
