migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("ebd7o5p4cf6e0x5")

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "dknpufxe",
    "name": "attraction_id",
    "type": "relation",
    "required": false,
    "unique": false,
    "options": {
      "collectionId": "9jkis8gsdoiyirt",
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
  const collection = dao.findCollectionByNameOrId("ebd7o5p4cf6e0x5")

  // remove
  collection.schema.removeField("dknpufxe")

  return dao.saveCollection(collection)
})
