migrate((db) => {
  const collection = new Collection({
    "id": "3gc3ooe3fj4mab6",
    "created": "2023-03-18 21:03:51.069Z",
    "updated": "2023-03-18 21:03:51.069Z",
    "name": "city_collection",
    "type": "base",
    "system": false,
    "schema": [
      {
        "system": false,
        "id": "rueyh3sz",
        "name": "image",
        "type": "file",
        "required": false,
        "unique": false,
        "options": {
          "maxSelect": 1,
          "maxSize": 5242880,
          "mimeTypes": [
            "image/jpeg",
            "image/png",
            "image/svg+xml",
            "image/gif",
            "image/webp"
          ],
          "thumbs": []
        }
      },
      {
        "system": false,
        "id": "rhxqk1qj",
        "name": "city_id",
        "type": "relation",
        "required": true,
        "unique": false,
        "options": {
          "collectionId": "gdvmachyqq8428a",
          "cascadeDelete": false,
          "maxSelect": 1,
          "displayFields": [
            "id"
          ]
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
  const collection = dao.findCollectionByNameOrId("3gc3ooe3fj4mab6");

  return dao.deleteCollection(collection);
})
