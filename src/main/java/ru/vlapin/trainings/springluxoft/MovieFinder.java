package ru.vlapin.trainings.springluxoft;

import java.util.Collection;

public interface MovieFinder {
  Collection<? extends Movie> findAll();
}
