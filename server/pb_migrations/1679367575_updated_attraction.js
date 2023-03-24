migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "apj1tons",
    "name": "rank",
    "type": "number",
    "required": true,
    "unique": false,
    "options": {
      "min": null,
      "max": null
    }
  }))

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  // remove
  collection.schema.removeField("apj1tons")

  return dao.saveCollection(collection)
})
