migrate((db) => {
  const collection = new Collection({
    "id": "ebd7o5p4cf6e0x5",
    "created": "2023-02-23 23:55:41.360Z",
    "updated": "2023-02-23 23:55:41.360Z",
    "name": "planDetail",
    "type": "base",
    "system": false,
    "schema": [
      {
        "system": false,
        "id": "lcnmwo7y",
        "name": "title",
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
        "id": "ug7nrfox",
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
        "id": "jfruyivr",
        "name": "plan_id",
        "type": "relation",
        "required": false,
        "unique": false,
        "options": {
          "collectionId": "42e01lrhq9mkbr6",
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
  const collection = dao.findCollectionByNameOrId("ebd7o5p4cf6e0x5");

  return dao.deleteCollection(collection);
})
