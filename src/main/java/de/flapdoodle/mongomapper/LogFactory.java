/**
 * Created by Concept People Consulting 2014
 */
package de.flapdoodle.mongomapper;

import java.util.logging.Logger;

/**
 * Factory zum Erzeugen eines Loggers.
 */
public abstract class LogFactory {

    private LogFactory() {
        // no instance
    }

    /**
     * Erzeugt eine Expetion, um Zugriff auf den StackTrace zubekommen.
     * Um an den Namen des Aufrufers zukommen, wird auf den mit callStackDepth referenzierten Eintrag im StackTrace zugegriffen.
     *
     * Beispiel: Wird die Methode aus einer anderen Klasse aufgerufen, sollte callStackDepth = 1 sein
     * Beim Aufruf durch die Methode logger der selben Klasse muss callStackDepth = 2 sein, um an den Aufrufer zu gelangen.
     *
     * @param callStackDepth
     * @return Klassenname des Eintrages von callStackDepth
     */
    private static String classNameOfCaller(int callStackDepth){
        return new Exception().getStackTrace()[callStackDepth].getClassName();
    }

    /**
     * Logger für den Aufrufer
     * @return
     */
    public static Logger logger() {
        return Logger.getLogger(classNameOfCaller(2));
    }

    /**
     * Logger für die Klasse clazz
     * @param clazz
     * @return
     */
    public static Logger logger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }

    /**
     * Logger für die Klasse clazz
     * @param clazz
     * @return
     */
    public static Logger logger(Class<?> clazz,String key) {
        return Logger.getLogger(clazz.getName()+"-"+key);
    }
}
