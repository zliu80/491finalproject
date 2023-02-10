migrate((db) => {
  const collection = new Collection({
    "id": "42e01lrhq9mkbr6",
    "created": "2023-02-07 02:17:53.128Z",
    "updated": "2023-02-07 02:17:53.128Z",
    "name": "travelPlans",
    "type": "base",
    "system": false,
    "schema": [
      {
        "system": false,
        "id": "qvbnv7ii",
        "name": "name",
        "type": "text",
        "required": false,
        "unique": false,
        "options": {
          "min": null,
          "max": null,
          "pattern": ""
        }
      }
    ],
    "listRule": null,
    "viewRule": null,
    "createRule": null,
    "updateRule": null,
    "deleteRule": null,
    "options": {}
  });

  return Dao(db).saveCollection(collection);
}, (db) => {
  const dao = new Dao(db);
  const collection = dao.findCollectionByNameOrId("42e01lrhq9mkbr6");

  return dao.deleteCollection(collection);
})
