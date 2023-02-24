migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a")

  // update
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "ac5i4mov",
    "name": "city_image",
    "type": "url",
    "required": false,
    "unique": false,
    "options": {
      "exceptDomains": null,
      "onlyDomains": null
    }
  }))

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a")

  // update
  collection.schema.addField(new SchemaField({
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
  }))

  return dao.saveCollection(collection)
})
