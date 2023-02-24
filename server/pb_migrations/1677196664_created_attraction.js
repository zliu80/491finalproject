migrate((db) => {
  const collection = new Collection({
    "id": "9jkis8gsdoiyirt",
    "created": "2023-02-23 23:57:44.003Z",
    "updated": "2023-02-23 23:57:44.003Z",
    "name": "attraction",
    "type": "base",
    "system": false,
    "schema": [
      {
        "system": false,
        "id": "zp2clqbl",
        "name": "name",
        "type": "text",
        "required": false,
        "unique": false,
        "options": {
          "min": null,
          "max": null,
          "pattern": ""
        }
      },
      {
        "system": false,
        "id": "mbj1zcuh",
        "name": "description",
        "type": "text",
        "required": false,
        "unique": false,
        "options": {
          "min": null,
          "max": null,
          "pattern": ""
        }
      },
      {
        "system": false,
        "id": "dc1yk7yz",
        "name": "address",
        "type": "text",
        "required": false,
        "unique": false,
        "options": {
          "min": null,
          "max": null,
          "pattern": ""
        }
      },
      {
        "system": false,
        "id": "3vivvz76",
        "name": "attraction_image",
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
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt");

  return dao.deleteCollection(collection);
})
