

export interface Page<T> {
    content: T[];
    totalElements: number;
    totalPages: number
    size: number
    number: number
    sort: any
    first: boolean
    last: boolean
    numberOfElements: number
    pageable: any
    empty: boolean
}