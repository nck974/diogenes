"""
Module to create some test data to ease the development
"""
import json
import random
from pydantic import BaseModel, Field
from faker import Faker
from faker.providers import DynamicProvider
import requests

SERVER = "http://localhost:8080/diogenes/api/v1"

class Item(BaseModel):
    """
    Representation of an item
    """
    name: str
    description: str
    category_id: int = Field(alias="categoryId")
    number: int

class Category(BaseModel):
    """
    Representation of a class
    """
    id: int
    name: str
    description: str
    color: str


def generate_random_color() -> str:
    """
    Generate a random color
    """
    r = random.randint(0,255)
    g = random.randint(0,255)
    b = random.randint(0,255)
    return '%02X%02X%02X' % (r,g,b)


def post_categories(categories: list[Category]):
    """
    Create a set of categories
    """
    url = f"{SERVER}/categories/"
    headers = {"Content-Type": "application/json"}
    for category in categories:
        data = category.model_dump(by_alias=True)
        try:
            res = requests.post(url, json = data, headers = headers, timeout=30)
            res.raise_for_status()
        except requests.exceptions.HTTPError as err:
            print(f"{category.name} could not be created {err.response.status_code}")
            continue

def post_items(items: list[Item]):
    """
    Create a set of items
    """
    url = f"{SERVER}/item/"
    headers = {"Content-Type": "application/json"}
    for item in items:
        data = item.model_dump(by_alias=True)
        try:
            res = requests.post(url, json = data, headers = headers, timeout=30)
            res.raise_for_status()
        except requests.exceptions.HTTPError as err:
            print(f"{item.name} could not be created {err.response.status_code}")
            continue


def get_all_categories() -> list[Category]:
    """
    Retrieve all categories
    """
    url = f"{SERVER}/categories/"
    res = requests.get(url, timeout=30)
    res.raise_for_status()
    raw_categories = json.loads(res.content)

    categories = []
    for raw_category in raw_categories:
        category = Category.model_validate(raw_category)
        categories.append(category)
    return categories


def read_names(file: str) -> list[str]:
    """
    Read the locally stored categories
    """
    with open(file, encoding="utf8", mode="r") as f:
        return f.readlines()


def create_categories() -> list[Category]:
    """
    Build categories
    """
    categories = []
    fake = Faker()
    categories_provider = DynamicProvider(
        provider_name="categories",
        elements=read_names("categories.txt"),
    )
    fake.add_provider(categories_provider)
    for counter in range(20):
        category = Category(
            id = counter,
            name = fake.categories().strip(),
            description= fake.text(),
            color=generate_random_color()
        )
        categories.append(category)
    return categories


def create_items(categories: list[Category]) -> list[Item]:
    """
    Build items
    """
    items = []
    fake = Faker()
    items_provider = DynamicProvider(
        provider_name="random_item",
        elements=read_names("items.txt"),
    )
    fake.add_provider(items_provider)
    for _ in range(20):
        item = Item(
            name = fake.random_item().strip(),
            categoryId=categories[random.randint(0, len(categories)-1)].id,
            description= fake.text(),
            number=random.randint(0,1000),
        )
        items.append(item)
    return items


def main():
    """
    Main code execution
    """
    categories = create_categories()
    post_categories(categories)
    categories = get_all_categories()
    items = create_items(categories)
    post_items(items)

if __name__ == "__main__":
    main()
