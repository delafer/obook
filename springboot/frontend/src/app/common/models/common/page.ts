import { JsonObject, JsonProperty } from 'json2typescript';

@JsonObject('Page')
export class Page<E> {
  @JsonProperty('content')
  content: E[];

  pageable: Pageable;
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  sort?: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}

export interface Pageable {
  sort: Sort;
  offset: number;
  pageSize: number;
  pageNumber: number;
  unpaged?: boolean;
  paged?: boolean;
}

export interface Sort {
  sorted?: boolean;
  unsorted?: boolean;
  empty?: boolean;
}
