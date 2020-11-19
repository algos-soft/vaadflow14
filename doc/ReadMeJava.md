JAVA
======================
##Versioni
![](/Users/gac/Documents/IdeaProjects/Operativi/Vaadflow14/doc/Oracle.png)


- Java version history [wikipedia](https://en.wikipedia.org/wiki/Java_version_history)
- Guide to java versions [dzone](https://dzone.com/articles/a-guide-to-java-versions-and-features)
- Java LTS Releases [ippon](https://blog.ippon.tech/comparing-java-lts-releases/)
- Java’s Time-Based Releases [baeldung](https://www.baeldung.com/java-time-based-releases)
- Versions and Features [marcobehler](https://www.marcobehler.com/guides/a-guide-to-java-versions-and-features)
- Java versions [codejava](https://www.codejava.net/java-se/java-se-versions-history)

##Lambdas
- Staring from 8
##Interfaces
- Staring from 8

##Collection
- Staring from 8/9


    List<String> list = List.of("one", "two", "three");
    Set<String> set = Set.of("one", "two", "three");
    Map<String, String> map = Map.of("foo", "one", "bar", "two");
##Streams
- Staring from 8/9


    Stream<String> stream = Stream.iterate("", s -> s + "s").takeWhile(s -> s.length() < 10);
##Optionals
- Staring from 8/9


    user.ifPresentOrElse(this::displayAccount, this::displayLogin);
##JShell
- Staring from 9

      % jshell
      |  Welcome to JShell -- Version 9
      |  For an introduction type: /help intro

      jshell> int x = 10
      x ==> 10
      
##Modules
      
##var keyword
- Staring from 10


    // Pre-Java 10

    String myName = "Marco";

    // With Java 10

    var myName = "Marco"
##Strings
- Staring from 11

##Multiline Strings
- Staring from 13

##Switch Expression
- Staring from 13/14

    
    boolean result = switch (status) {
        case SUBSCRIBER -> true;
        case FREE_TRIAL -> false;
        default -> throw new IllegalArgumentException("something is murky!");
    };    
   
    int numLetters = switch (day) {
        case MONDAY, FRIDAY, SUNDAY -> 6;
        case TUESDAY                -> 7;
        default      -> {
          String s = day.toString();
          int result = s.length();
          yield result;
        }
    };
##Record
- Staring from 14

    final class Point {
        public final int x;
        public final int y;    

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
      record Point(int x, int y) { }
##InstanceOf      
- Staring from 14
    
    
    if (obj instanceof String) {
        String s = (String) obj;
        // use s
    }
    
    if (obj instanceof String s) {
        System.out.println(s.contains("hello"));
    }