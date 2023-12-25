import { Category } from "./Category";
import { Location } from "./Location";

export interface Item {
    id: number;
    name: string;
    description: string;
    number: number;
    category: Category;
    location: Location;
    createdOn: Date;
    updatedOn: Date;
    imagePath: string | undefined;
    categoryId: number | undefined;
}