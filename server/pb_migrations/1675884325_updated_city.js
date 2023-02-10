migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a")

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "70fzjjqo",
    "name": "rank",
    "type": "number",
    "required": false,
    "unique": false,
    "options": {
      "min": null,
      "max": null
    }
  }))

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "ue0m65ui",
    "name": "description",
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
  collection.schema.removeField("70fzjjqo")

  // remove
  collection.schema.removeField("ue0m65ui")

  return dao.saveCollection(collection)
})
