migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  // remove
  collection.schema.removeField("3vivvz76")

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "hyizkg21",
    "name": "attraction_image",
    "type": "file",
    "required": false,
    "unique": false,
    "options": {
      "maxSelect": 1,
      "maxSize": 5242880,
      "mimeTypes": [
        "image/jpeg",
        "image/png",
        "image/gif",
        "image/webp",
        "image/bmp"
      ],
      "thumbs": []
    }
  }))

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  // add
  collection.schema.addField(new SchemaField({
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
  }))

  // remove
  collection.schema.removeField("hyizkg21")

  return dao.saveCollection(collection)
})
