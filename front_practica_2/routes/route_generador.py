from .route_familia import *

router_generador = Blueprint('router_generador',__name__)

G_URL = f'{BASE_URL}/generador'

# TABLE GENERADOR ================================================================================

@router_generador.route('/generador/table')
def table_generador():
    response = requests.get(f'{G_URL}/all')
    attributes = requests.get(f'{G_URL}/attributes').json()['data']
    generadores = response.json()['data']
    for i in range(0,len(generadores)):
        generadores[i]['numero'] = i+1
    return render_template('generador/table_generador.html',generadores=generadores,attributes=attributes)


# BÚSQUEDA Y ORDENACIÓN ========================================================================

@router_familia.route('/generador/search/<attribute>/<value>')
def generador_search(attribute,value):
    response = requests.get(f'{G_URL}/search/{attribute}/{value}')
    attributes = requests.get(f'{G_URL}/attributes').json()['data']
    generadores = response.json()['data']
    for i in range(0,len(generadores)): 
        generadores[i]['numero'] = i+1
    return render_template('generador/table_generador.html',generadores=generadores,attributes=attributes)

@router_familia.route('/generador/sort/<attribute>/<orden>/<typesort>')
def generador_sort(attribute,orden,typesort):
    response = requests.get(f'{G_URL}/sort/{attribute}/{orden}/{typesort}')
    attributes = requests.get(f'{G_URL}/attributes').json()['data']
    generadores = response.json()['data']
    for i in range(0,len(generadores)): 
        generadores[i]['numero'] = i+1
    return render_template('generador/table_generador.html',generadores=generadores,attributes=attributes)

# SAVE GENERADOR =================================================================================

@router_generador.route('/generador/save')
def save_generador():
    return render_template('/generador/save_generador.html')
    
@router_generador.route('/generador/save/send',methods=['POST'])
def save_generador_send():
    data = request.form.to_dict()
    headers = {'Content-Type':'application/json'}
    response = requests.post(f'{G_URL}/save/',json=data,headers=headers)
    msg = response.json()
    key = ['info','info'] if response.status_code == 201 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/generador/table')

# UPDATE GENERADOR ==============================================================================

@router_generador.route('/generador/update/<int:id>')
def update_generador(id):
    response = requests.get(f'{G_URL}/get/{id}')
    generador = response.json()['data']
    return render_template('generador/update_generador.html',generador=generador)

@router_generador.route('/generador/update/send',methods=['POST'])
def update_generador_send():
    json = request.form.to_dict()
    headers = {'Content-Type':'application/json'}
    response = requests.post(f'{G_URL}/update',json=json,headers=headers)
    msg = response.json()
    key = ['info','info'] if response.status_code == 200 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/generador/table')

# DELETE GENERADOR =============================================================================

@router_generador.route('/generador/delete/<int:id>')
def delete_generador(id):
    response = requests.get(f'{G_URL}/get/{id}')
    generador = response.json()['data']
    return render_template('generador/delete_generador.html',generador=generador)

@router_generador.route('/generador/delete/send',methods=['POST'])
def delete_generador_send():
    response = requests.delete(f'{G_URL}/delete/{request.form['id']}')
    msg = response.json()
    key = ['info','info'] if response.status_code == 200 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/generador/table')

