package askmyapi.amaserver.util;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class InfiniteScroll<T> {
    private final List<T> data;
    private final boolean hasNext;

    @Builder
    private InfiniteScroll(List<T> data, boolean hasNext) {
        this.data = data;
        this.hasNext = hasNext;
    }

    public <R> InfiniteScroll<R> map(Function<? super T, ? extends R> mapper) {
        return new InfiniteScroll<>(
                data.stream().map(mapper).collect(Collectors.toList()),
                hasNext
        );
    }
}
