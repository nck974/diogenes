import { Category } from "./Category";

export interface Item{
    id: number;
    name: string;
    description: string;
    number: number;
    category: Category;
    createdOn: Date;
    updatedOn: Date;
}