# TP : Injection de dépendances et couplage faible

## 1. Introduction

Ce TP a pour objectif de comprendre le principe de l'injection de dépendances et du couplage faible en Java.

L'objectif est de créer une application composée de plusieurs couches et de réaliser l'injection des dépendances de différentes manières :

* Instanciation statique
* Instanciation dynamique
* Injection avec Spring XML
* Injection avec Spring et les annotations

---

## 2. Création de l'interface `IDao`

Nous avons commencé par créer l'interface `IDao`.

```java
package org.example.dao;

public interface IDao {
    double getData();
}
```

Cette interface définit la méthode `getData()` qui retourne une valeur de type `double`.

---

## 3. Implémentation de `IDao`

Nous avons créé la classe `DaoImpl` qui implémente l'interface `IDao`.

```java
package org.example.dao;

public class DaoImpl implements IDao {

    @Override
    public double getData() {
        return 10;
    }
}
```

La méthode `getData()` retourne la valeur `10`.

---

## 4. Création de l'interface `IMetier`

Nous avons créé l'interface `IMetier`.

```java
package org.example.metier;

public interface IMetier {
    double calcul();
}
```

Cette interface définit la méthode `calcul()`.

---

## 5. Implémentation de `IMetier` avec couplage faible

La classe `MetierImpl` utilise l'interface `IDao` au lieu de dépendre directement de la classe `DaoImpl`.

```java
package org.example.metier;

import org.example.dao.IDao;

public class MetierImpl implements IMetier {

    private IDao dao;

    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData() * 2;
    }
}
```

Cette approche permet d'obtenir un **couplage faible**.

`MetierImpl` dépend de l'abstraction `IDao` et non d'une implémentation concrète.

---

## 6. Injection statique

Dans cette première méthode, les objets sont créés directement dans la classe de présentation.

```java
IDao dao = new DaoImpl();

IMetier metier = new MetierImpl(dao);

System.out.println("Résultat = " + metier.calcul());
```

### Résultat

```text
Résultat = 20.0
```

---

## 7. Injection dynamique

Nous avons ensuite utilisé la réflexion Java pour créer dynamiquement les objets.

```java
Class<?> cDao = Class.forName("org.example.dao.DaoImpl");
IDao dao = (IDao) cDao.getDeclaredConstructor().newInstance();

Class<?> cMetier = Class.forName("org.example.metier.MetierImpl");
IMetier metier = (IMetier) cMetier
        .getDeclaredConstructor(IDao.class)
        .newInstance(dao);

System.out.println("Résultat = " + metier.calcul());
```

Cette méthode permet de créer les objets dynamiquement à partir de leurs noms de classes.

### Résultat

```text
Résultat = 20.0
```

---

## 8. Injection avec Spring XML

Nous avons ajouté la dépendance Spring dans le fichier `pom.xml`.

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>6.2.10</version>
</dependency>
```

Ensuite, nous avons créé le fichier :

```text
src/main/resources/applicationContext.xml
```

avec la configuration suivante :

```xml
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dao" class="org.example.dao.DaoImpl"/>

    <bean id="metier" class="org.example.metier.MetierImpl">
        <constructor-arg ref="dao"/>
    </bean>

</beans>
```

Spring se charge alors de créer les objets et d'injecter automatiquement `dao` dans `MetierImpl`.

### Résultat

```text
Résultat = 20.0
```

---

## 9. Injection avec Spring et les annotations

Pour cette partie, nous avons utilisé une configuration Java avec les annotations Spring.

La classe `AppConfig` contient :

```java
package org.example;

import org.example.dao.DaoImpl;
import org.example.dao.IDao;
import org.example.metier.IMetier;
import org.example.metier.MetierImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public IDao dao() {
        return new DaoImpl();
    }

    @Bean
    public IMetier metier(IDao dao) {
        return new MetierImpl(dao);
    }
}
```

La classe de présentation utilise ensuite cette configuration :

```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

IMetier metier = context.getBean("metier", IMetier.class);

System.out.println("Résultat = " + metier.calcul());
```

### Résultat

```text
Résultat = 20.0
```

---

## 10. Structure du projet

```text
TP-Injection-Dependances
│
├── src
│   └── main
│       ├── java
│       │   └── org.example
│       │       ├── dao
│       │       │   ├── IDao.java
│       │       │   └── DaoImpl.java
│       │       │
│       │       ├── metier
│       │       │   ├── IMetier.java
│       │       │   └── MetierImpl.java
│       │       │
│       │       ├── presentation
│       │       │   └── Presentation.java
│       │       │
│       │       └── AppConfig.java
│       │
│       └── resources
│           └── applicationContext.xml
│
├── pom.xml
└── README.md
```

---

## 11. Conclusion

Ce TP nous a permis de comprendre le principe de l'injection de dépendances et l'intérêt du couplage faible.

Nous avons réalisé plusieurs méthodes d'injection :

1. Instanciation statique
2. Instanciation dynamique avec Reflection
3. Injection avec Spring XML
4. Injection avec Spring et une configuration Java basée sur les annotations

L'utilisation de l'interface `IDao` permet à la classe `MetierImpl` de ne pas dépendre directement de `DaoImpl`. Cela rend l'application plus flexible, maintenable et évolutive.

### Résultat final

Toutes les méthodes d'injection testées donnent le résultat :

```text
Résultat = 20.0
```

---

## GitHub

Projet réalisé dans le cadre du TP sur l'injection de dépendances et le couplage faible.
