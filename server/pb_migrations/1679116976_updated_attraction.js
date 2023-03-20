migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "jyqk6ump",
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
  }))

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  // remove
  collection.schema.removeField("jyqk6ump")

  return dao.saveCollection(collection)
})
