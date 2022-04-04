package ru.vlapin.trainings.springluxoft.common;

import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

@UtilityClass
public class AspectUtils {

  @SneakyThrows
  public Optional<Method> getMethod(JoinPoint jp) {
    if (jp.getSignature() instanceof MethodSignature signature) {
      val method = signature.getMethod();
      return Optional.of(method.getDeclaringClass().isInterface() ?
                              jp.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes()) : method);
    } else
      return Optional.empty();
  }

  public <A extends Annotation> A getAnnotation(JoinPoint jp, Class<A> annotationClass) {
    return getMethod(jp)
        .flatMap(method -> Optional.ofNullable(
            Optional.ofNullable(method.getAnnotation(annotationClass))
                .orElseGet(() -> method.getDeclaringClass().getAnnotation(annotationClass))))
        .orElseThrow();
  }

  public <A extends Annotation> Tuple2<A, Method> getAnnotationAndMethod(JoinPoint jp, Class<A> annotationClass) {
    return getMethod(jp)
        .map(method -> Tuple.of(method.getAnnotation(annotationClass), method))
        .orElseThrow();
  }

  public <A extends Annotation, R> R destruct(JoinPoint jp,
                                              Class<A> annotationClass,
                                              CheckedFunction2<A, Method, R> method) {
    return getAnnotationAndMethod(jp, annotationClass)
        .apply(method.unchecked());
  }

  public <R> R destruct(JoinPoint jp, CheckedFunction1<Method, R> method) {
    return method.unchecked().apply(getMethod(jp).orElseThrow());
  }

  public <A extends Annotation> A getAnnotation(Method method, Class<A> aClass) {
    return Optional.ofNullable(method.getAnnotation(aClass))
        .orElseThrow();
  }
}
