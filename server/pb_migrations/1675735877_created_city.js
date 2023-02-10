migrate((db) => {
  const collection = new Collection({
    "id": "gdvmachyqq8428a",
    "created": "2023-02-07 02:11:17.601Z",
    "updated": "2023-02-07 02:11:17.601Z",
    "name": "city",
    "type": "base",
    "system": false,
    "schema": [
      {
        "system": false,
        "id": "mfke9mzw",
        "name": "name",
        "type": "text",
        "required": true,
        "unique": true,
        "options": {
          "min": null,
          "max": null,
          "pattern": ""
        }
      },
      {
        "system": false,
        "id": "ac5i4mov",
        "name": "image",
        "type": "url",
        "required": false,
        "unique": false,
        "options": {
          "exceptDomains": null,
          "onlyDomains": null
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
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a");

  return dao.deleteCollection(collection);
})
