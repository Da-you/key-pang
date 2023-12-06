package portfolio.keypang.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

  private int pageNumber; // 현재 페이지 번호
  private int numberOfElements; // 현재 페이지에 나올 데이터 수
  private int size; // 페이지 크기
  private long totalElements; // 전체 데이터 수
  private int totalPages; // 전체 페이지 수
  private List<T> contents;

  public class defaultValue {

    public static final int DEFAULT_PAGE = 10;
  }

  public static <E, D> PageResponse<D> of(Page<E> entity, List<D> contents) {
    return new PageResponse<> (entity.getNumber(),
        entity.getNumberOfElements(),
        entity.getSize(),
        entity.getTotalElements(),
        entity.getTotalPages(), contents);
  }
}
