export declare type ComparatorType = (
  actual: string,
  expected: string
) => boolean;
export declare type MapperType = (input: string, obj?: any) => any;
export const caseInsensitiveCompleteMatch: ComparatorType = function (
  actual,
  expected
) {
  return actual.toLowerCase() === expected.toLowerCase();
};

export const caseSensitiveMatch: ComparatorType = function (actual, expected) {
  return actual === expected;
};

export const notNullMatch: ComparatorType = function (actual, expected) {
  if (actual) return true;
  else return false;
};

const defaultMatcher: ComparatorType = function (actual, expected) {
  return actual.toLowerCase().indexOf(expected.toLowerCase()) !== -1;
};

export class ColumnFilter {
  static caseInsensitiveCompleteMatch = caseInsensitiveCompleteMatch;

  column: string;
  filter: string;
  mapper?: MapperType;

  matcher?: ComparatorType;

  constructor(
    column?: string,
    filter?: string,
    mapper?: MapperType,
    matcher: ComparatorType = defaultMatcher
  ) {
    this.column = column;
    this.filter = filter;
    this.mapper = mapper;
    this.matcher = matcher;
  }
}
