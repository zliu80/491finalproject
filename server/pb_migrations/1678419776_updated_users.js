migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("_pb_users_auth_")

  // update
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "rvvs3hac",
    "name": "phoneNumber",
    "type": "text",
    "required": false,
    "unique": false,
    "options": {
      "min": 9,
      "max": null,
      "pattern": ""
    }
  }))

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("_pb_users_auth_")

  // update
  collection.schema.addField(new SchemaField({
    "system": false,
    "id": "rvvs3hac",
    "name": "phone_number",
    "type": "text",
    "required": false,
    "unique": false,
    "options": {
      "min": 9,
      "max": null,
      "pattern": ""
    }
  }))

  return dao.saveCollection(collection)
})
