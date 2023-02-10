migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a")

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "rodzy9hi",
    "name": "fun_facts",
    "type": "text",
    "required": false,
    "unique": false,
    "options": {
      "min": null,
      "max": null,
      "pattern": ""
    }
  }))

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a")

  // remove
  collection.schema.removeField("rodzy9hi")

  return dao.saveCollection(collection)
})
