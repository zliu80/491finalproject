migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a")

  // remove
  collection.schema.removeField("ac5i4mov")

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "ryozc5pv",
    "name": "city_image",
    "type": "file",
    "required": false,
    "unique": false,
    "options": {
      "maxSelect": 1,
      "maxSize": 5242880,
      "mimeTypes": [
        "image/jpeg",
        "image/vnd.mozilla.apng",
        "image/png"
      ],
      "thumbs": [
        "50x50"
      ]
    }
  }))

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("gdvmachyqq8428a")

  // add
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

  // remove
  collection.schema.removeField("ryozc5pv")

  return dao.saveCollection(collection)
})
