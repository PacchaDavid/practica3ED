from flask import Blueprint, redirect, render_template, request, flash
import requests

router_familia = Blueprint('router_familia',__name__)

@router_familia.route('/')
def home():
    return render_template('index.html')

BASE_URL = 'http://localhost:8080/api'

F_URL = f'{BASE_URL}/familia'

# TABLE FAMILIA =============================================================================================

@router_familia.route('/familia/table')
def table_familia():
    response = requests.get(f'{F_URL}/all')
    attributes = requests.get(f'{F_URL}/attributes').json()['data']
    familias = response.json()['data']
    for i in range(0,len(familias)): 
        familias[i]['numero'] = i+1
    return render_template('familia/table_familia.html',familias=familias,attributes=attributes)


# BÚSQUEDA Y ORDENACIÓN ====================================================================================

@router_familia.route('/familia/search/<attribute>/<value>')
def search(attribute,value):
    response = requests.get(f'{F_URL}/search/{attribute}/{value}')
    attributes = requests.get(f'{F_URL}/attributes').json()['data']
    familias = response.json()['data']
    for i in range(0,len(familias)): 
        familias[i]['numero'] = i+1
    return render_template('familia/table_familia.html',familias=familias,attributes=attributes)

@router_familia.route('/familia/sort/<attribute>/<orden>/<typesort>')
def sort(attribute,orden,typesort):
    response = requests.get(f'{F_URL}/sort/{attribute}/{orden}/{typesort}')
    attributes = requests.get(f'{F_URL}/attributes').json()['data']
    familias = response.json()['data']
    for i in range(0,len(familias)): 
        familias[i]['numero'] = i+1
    return render_template('familia/table_familia.html',familias=familias,attributes=attributes)


# SAVE FAMILIA ============================================================================================

@router_familia.route('/familia/save')
def save_familia():
    response = requests.get(f'{F_URL}/enumerations')
    return render_template('familia/save_familia.html',enumeracion=response.json()['data'])

@router_familia.route('/familia/save/send', methods=['POST'])
def save_familia_send():
    headers = {'Content-Type':'application/json'}
    data = request.form.to_dict()
    response = requests.post(f'{F_URL}/save',json=data,headers=headers)
    msg = response.json()
    key = ['info','info'] if response.status_code == 201 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/familia/table')
    
# FAMILIA UPDATE ========================================================================

@router_familia.route('/familia/update/<int:id>')
def update_familia(id):
    response = requests.get(f'{F_URL}/get/{id}')
    familia = response.json()['data']
    response = requests.get(f'{F_URL}/enumerations')
    print(response)
    enums = response.json()['data']
    
    return render_template('familia/update_familia.html',context={"familia": familia, "enums" : enums})

@router_familia.route('/familia/update/send',methods=['POST'])
def update_familia_send():
    data = request.form.to_dict()
    headers = {'Content-Type':'application/json'}
    response = requests.post(f'{F_URL}/update',json=data,headers=headers)
    msg = response.json()
    key = ['info','info'] if response.status_code == 200 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
        
    return redirect('/familia/table')

# DELETE FAMILIA ========================================================================

@router_familia.route('/delete/familia/<int:id>')
def delete_familia(id):
    response = requests.get(f'{F_URL}/get/{id}')
    familia = response.json()['data']
    return render_template('familia/delete_familia.html',familia=familia)

@router_familia.route('/delete/familia/send',methods=['POST'])
def delete_familia_send():
    response = requests.delete(f'{F_URL}/delete/{request.form['id']}')
    msg = response.json()
    key = ['info','info'] if response.status_code == 200 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/familia/table')



# HISTORIAL DE ACCIONES ==============================================================================

H_URL = f'{BASE_URL}/historial'

@router_familia.route('/historial/table')
def table_historial():
    acciones = requests.get(f'{H_URL}/all').json()['data']
    attributes = requests.get(f'{H_URL}/attributes').json()['data']
    for i in range(0,len(acciones)):
        acciones[i]['numero'] = i+1
    return render_template('historial/table_historial.html',acciones=acciones,attributes=attributes)

# DELETE ACCION =================================================================================

@router_familia.route('/historial/delete/<id>')
def delete_historial(id):
    accion = requests.get(f'{H_URL}/get/{id}').json()['data']
    print(accion)
    return render_template('historial/delete_historial.html',accion=accion)

@router_familia.route('/historial/delete/send/<id>')
def delete_historial_send(id):
    response = requests.delete(f'{H_URL}/delete/{id}')
    msg = response.json()
    key = ['info','info'] if response.status_code == 200 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/historial/table')

# BUSQUEDA Y ORDENACIÓN =================================================================

@router_familia.route('/historial/search/<attribute>/<value>')
def historial_search(attribute,value):
    response = requests.get(f'{H_URL}/search/{attribute}/{value}')
    attributes = requests.get(f'{H_URL}/attributes').json()['data']
    acciones = response.json()['data']
    for i in range(0,len(acciones)): 
        acciones[i]['numero'] = i+1
    return render_template('historial/table_historial.html',acciones=acciones,attributes=attributes)

@router_familia.route('/historial/sort/<attribute>/<orden>/<typesort>')
def historial_sort(attribute,orden,typesort):
    response = requests.get(f'{H_URL}/sort/{attribute}/{orden}/{typesort}')
    attributes = requests.get(f'{H_URL}/attributes').json()['data']
    acciones = response.json()['data']
    for i in range(0,len(acciones)): 
        acciones[i]['numero'] = i+1
    return render_template('historial/table_historial.html',acciones=acciones,attributes=attributes)


# Ay! bendita sea mi mama
# por haberme parido macho!
# Aaay, mama!

#🗣️🗣️🗣️🗣️🗣️🗣️🗣️