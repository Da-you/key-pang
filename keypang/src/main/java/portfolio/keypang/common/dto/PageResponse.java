package portfolio.keypang.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {

  private int pageNumber; // 현재 페이지 번호
  private int numberOfElements; // 현재 페이지에 나올 데이터 수
  private int size; // 페이지 크기
  private long totalElements; // 전체 데이터 수
  private int totalPages; // 전체 페이지 수

  public class defaultValue {

    public static final int DEFAULT_PAGE = 10;
  }
}
