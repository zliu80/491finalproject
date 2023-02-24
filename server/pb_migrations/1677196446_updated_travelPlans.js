migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("42e01lrhq9mkbr6")

  collection.name = "plans"

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "smq92jja",
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

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "ytn6d0u0",
    "name": "start_date",
    "type": "date",
    "required": false,
    "unique": false,
    "options": {
      "min": "",
      "max": ""
    }
  }))

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "3fcr3css",
    "name": "end_date",
    "type": "date",
    "required": false,
    "unique": false,
    "options": {
      "min": "",
      "max": ""
    }
  }))

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "ztoc4xbh",
    "name": "city_id",
    "type": "relation",
    "required": false,
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

  // add
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "smwu9ivc",
    "name": "user_id",
    "type": "relation",
    "required": false,
    "unique": false,
    "options": {
      "collectionId": "_pb_users_auth_",
      "cascadeDelete": false,
      "maxSelect": 1,
      "displayFields": [
        "id"
      ]
    }
  }))

  // update
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "qvbnv7ii",
    "name": "title",
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
  const collection = dao.findCollectionByNameOrId("42e01lrhq9mkbr6")

  collection.name = "travelPlans"

  // remove
  collection.schema.removeField("smq92jja")

  // remove
  collection.schema.removeField("ytn6d0u0")

  // remove
  collection.schema.removeField("3fcr3css")

  // remove
  collection.schema.removeField("ztoc4xbh")

  // remove
  collection.schema.removeField("smwu9ivc")

  // update
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "qvbnv7ii",
    "name": "name",
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
})
