package it.algos.vaadflow14.backend.functional;

import java.util.function.Predicate;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: mar, 01-dic-2020
 * Time: 21:20
 * <p>
 * Predicates implemented with interfaces. <br>
 * Predicate<T> is a generic functional interface representing
 * a single argument function that returns a boolean value <br>
 */
public abstract class APredicate {

    public static Predicate<Object> valido = new Predicate<>() {

        /**
         * Evaluates this predicate on the given argument.
         *
         * @param obj the input argument
         *
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object obj) {
            if (obj instanceof String stringa) {
                return stringa != null && stringa.length() > 0;
            }
            else {
                return obj != null;
            }
        }
    };


    public static Predicate<Object> nonValido = new Predicate<>() {

        /**
         * Evaluates this predicate on the given argument.
         *
         * @param obj the input argument
         *
         * @return {@code true} if the input argument matches the predicate,
         * otherwise {@code false}
         */
        @Override
        public boolean test(Object obj) {
            if (obj instanceof String stringa) {
                return stringa == null || stringa.length() == 0;
            }
            else {
                return obj == null;
            }
        }
    };
    Predicate<Object> valido2 = nonValido.negate();
    Predicate<Object> valido3 = Predicate.not( nonValido );
}