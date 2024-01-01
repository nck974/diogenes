"""
Module to create some test data to ease the development
"""
import json
import random
from pydantic import BaseModel, Field
from faker import Faker
from faker.providers import DynamicProvider
import requests

SERVER = "http://localhost:8080/diogenes"
BASE_URL = f"{SERVER}/api/v1"

CONTENT_TYPE_JSON = "application/json"


class Item(BaseModel):
    """
    Representation of an item
    """

    name: str
    description: str
    category_id: int = Field(alias="categoryId")
    location_id: int = Field(alias="locationId")
    number: int


class Category(BaseModel):
    """
    Representation of a category
    """

    id: int
    name: str
    description: str
    color: str


class Location(BaseModel):
    """
    Representation of a location
    """

    id: int
    name: str
    description: str
    icon: str


def generate_random_color() -> str:
    """
    Generate a random color
    """
    r = random.randint(0, 255)
    g = random.randint(0, 255)
    b = random.randint(0, 255)
    return "%02X%02X%02X" % (r, g, b)


def post_categories(token: str, categories: list[Category]):
    """
    Create a set of categories
    """
    url = f"{BASE_URL}/categories/"
    headers = {"Content-Type": CONTENT_TYPE_JSON, "Authorization": f"Bearer {token}"}
    for category in categories:
        data = category.model_dump(by_alias=True)
        try:
            res = requests.post(url, json=data, headers=headers, timeout=30)
            res.raise_for_status()
        except requests.exceptions.HTTPError as err:
            if err.response:
                code = err.response.status_code
                print(f"{category.name} could not be created {code}")
            continue


def post_locations(token: str, locations: list[Location]):
    """
    Create a set of locations
    """
    url = f"{BASE_URL}/locations/"
    headers = {"Content-Type": CONTENT_TYPE_JSON, "Authorization": f"Bearer {token}"}
    for location in locations:
        data = location.model_dump(by_alias=True)
        try:
            res = requests.post(url, json=data, headers=headers, timeout=30)
            res.raise_for_status()
        except requests.exceptions.HTTPError as err:
            if err.response:
                code = err.response.status_code
                print(f"{location.name} could not be created {code}")
            continue


def post_items(token: str, items: list[Item]):
    """
    Create a set of items
    """
    url = f"{BASE_URL}/item/"
    headers = {"Content-Type": CONTENT_TYPE_JSON, "Authorization": f"Bearer {token}"}
    for item in items:
        data = item.model_dump(by_alias=True)
        try:
            res = requests.post(url, json=data, headers=headers, timeout=30)
            res.raise_for_status()
        except requests.exceptions.HTTPError as err:
            if err.response:
                print(f"{item.name} could not be created {err.response.status_code}")
            continue


def get_all_categories(token: str) -> list[Category]:
    """
    Retrieve all categories
    """
    url = f"{BASE_URL}/categories/"

    headers = {"Authorization": f"Bearer {token}"}

    res = requests.get(url, headers=headers, timeout=30)
    res.raise_for_status()
    raw_categories = json.loads(res.content)

    categories: list[Category] = []
    for raw_category in raw_categories:
        category = Category.model_validate(raw_category)
        categories.append(category)
    return categories


def get_all_locations(token: str) -> list[Location]:
    """
    Retrieve all locations
    """
    url = f"{BASE_URL}/locations/"

    headers = {"Authorization": f"Bearer {token}"}

    res = requests.get(url, headers=headers, timeout=30)
    res.raise_for_status()
    raw_locations = json.loads(res.content)

    locations: list[Location] = []
    for raw_location in raw_locations:
        location = Location.model_validate(raw_location)
        locations.append(location)
    return locations


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
    categories: list[Category] = []
    fake = Faker()
    categories_provider = DynamicProvider(
        provider_name="categories",
        elements=read_names("categories.txt"),
    )
    fake.add_provider(categories_provider)
    for counter in range(20):
        category = Category(
            id=counter,
            name=fake.categories().strip(),
            description=fake.text(),
            color=generate_random_color(),
        )
        categories.append(category)
    return categories


def create_locations() -> list[Location]:
    """
    Build locations
    """
    locations: list[Location] = []
    fake = Faker()
    locations_provider = DynamicProvider(
        provider_name="locations",
        elements=read_names("locations.txt"),
    )
    fake.add_provider(locations_provider)
    fake_2 = Faker()
    icons_provider = DynamicProvider(
        provider_name="icons",
        elements=read_names("icons.txt"),
    )
    fake_2.add_provider(icons_provider)
    for counter in range(20):
        location = Location(
            id=counter,
            name=fake.locations().strip(),
            description=fake.text(),
            icon=fake_2.icons().strip(),
        )
        locations.append(location)
    return locations


def create_items(categories: list[Category], locations: list[Location]) -> list[Item]:
    """
    Build items
    """
    items: list[Item] = []
    fake = Faker()
    items_provider = DynamicProvider(
        provider_name="random_item",
        elements=read_names("items.txt"),
    )
    fake.add_provider(items_provider)
    for _ in range(20):
        item = Item(
            name=fake.random_item().strip(),
            categoryId=categories[random.randint(0, len(categories) - 1)].id,
            locationId=locations[random.randint(0, len(locations) - 1)].id,
            description=fake.text(),
            number=random.randint(0, 1000),
        )
        items.append(item)
    return items


def get_token():
    """
    Query to get the token
    """
    url = f"{SERVER}/authenticate"
    data = {"username": "test1", "password": "test1"}
    res = requests.post(url, json=data, timeout=30)
    res.raise_for_status()

    return res.content.decode()


def main():
    """
    Main code execution
    """
    token = get_token()
    categories = create_categories()
    post_categories(token, categories)
    categories = get_all_categories(token)

    locations = create_locations()
    post_locations(token, locations)
    locations = get_all_locations(token)

    items = create_items(categories, locations)
    post_items(token, items)


if __name__ == "__main__":
    main()
