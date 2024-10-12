package agro.filelinkhub.domain;

import agro.filelinkhub.domain.exceptions.DuplicateKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComponentsMap<K, V> {

  private final Map<K, V> components;

  public ComponentsMap(List<V> list, Function<V, K> keyExtractor) throws DuplicateKeyException {
    components = list.stream()
            .collect(Collectors.toMap(
                    keyExtractor,
                    Function.identity(),
                    (existing, replacement) -> {
                      throw new DuplicateKeyException("Duplicate key found: " + keyExtractor.apply(existing));
                    },
                    HashMap::new
            ));
  }

  public V get(K key) {
    return components.get(key);
  }

}
